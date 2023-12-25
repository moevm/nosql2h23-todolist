<template>
  <v-container fluid>
    <h1>Анализ</h1>
    <v-row class="mb-2">
      <v-col>
        <v-combobox
            v-model="projectsToFilter"
            :items="$store.state.projects"
            item-text="name"
            :search-input.sync="search"
            prepend-inner-icon="mdi-magnify"
            clearable
            hide-selected
            multiple
            small-chips
            dense
            hide-details
            solo
            label="Проект"
        />
      </v-col>
      <v-col v-if="$store.state.userRole === 'admin'">
        <v-select
            v-model="personToFilter"
            :item-text="(item) => item.name + ' ' + item.surname"
            item-value="id"
            hide-details
            clearable
            solo
            dense
            return-object
            :items="$store.state.persons"
            prepend-inner-icon="mdi-magnify"
            label="Сотрудник"
        ></v-select>
      </v-col>
      <v-col>
        <el-date-picker
          class="elevation-1 mr-2"
          style="width: 100%"
          v-model="date"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="ПО"
          value-format="yyyy-MM-dd"
          start-placeholder="С"
          end-placeholder="ДО"
        />
      </v-col>
      <v-col>
        <v-combobox
          v-model="comboTasks"
          :items="comboItems"
          style="max-height: 30px"
          chips
          item-value="id"
          item-text="title"
          return-object
          multiple
          hide-selected
          placeholder="Название задачи"
          label="Название задачи"
          clearable
          dense
          hide-details
          solo
        />
      </v-col>
    </v-row>
    <h2>Соотношение количества задач в проектах</h2>
    <v-row class="mb-3">
      <v-col>
        <Doughnut
            :chart-options="chartOptions"
            :chart-data="chartData"
            :chart-id="doughnutChartId"
            :dataset-id-key="datasetIdKey"
            :plugins="plugins"
            :css-classes="cssClasses"
            :styles="styles"
        />
      </v-col>
    </v-row>
    <h2>Соотношение выполненных задач в проектах</h2>
    <v-row>
      <v-col>
        <Bar
            :chart-options="dogChartOptions"
            :chart-data="barChartData"
            :chart-id="barChartId"
            :dataset-id-key="datasetIdKey"
            :plugins="plugins"
            :css-classes="cssClasses"
            :styles="styles"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {defineComponent} from 'vue'
import { Doughnut, Bar } from 'vue-chartjs'

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'
import {mapState} from "vuex";
import moment from "moment/moment";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale, BarElement)

export default defineComponent({
  name: "StatsContent",
  components: {
    Doughnut, Bar
  },
  props: {
    doughnutChartId: {
      type: String,
      default: 'doughnut-chart'
    },
    barChartId: {
      type: String,
      default: 'doughnut-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => {
        return {
          height: '500px'
        };
      }
    },
    plugins: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      comboTasks: null,
      comboItems: null,
      date: null,
      projectsToFilter: [],
      personToFilter: null,
      search: '',
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
      dogChartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [{
            gridLines: {
              color: "rgba(0, 0, 0, 0)",
            }
          }],
          yAxes: [{
            gridLines: {
              color: "rgba(0, 0, 0, 0)",
            }
          }]
        }
      },
    }
  },
  methods: {
    randomNum() {
      return Math.floor(Math.random() * (235 - 52 + 1) + 52);
    },

    randomRGB() {
      return `rgb(${this.randomNum()}, ${this.randomNum()}, ${this.randomNum()})`;
    },
    getTasksRange(project) {
      let completed = 0;
      let incompleted = 0;
      project.tasks.forEach((el) => {
        el.status === 'COMPLETE' ? completed += 1 : incompleted += 1;
      })
      return [completed, incompleted];
    },
    getTaskCount(project) {
      return project.tasks.length;
    },
  },
  computed: {
    ...mapState(['projects', 'persons']),
    computedProjectsData() {
      let proj = JSON.parse(JSON.stringify(this.projects));
      if (this.projectsToFilter.length !== 0) {
        proj = proj.filter((el) => this.projectsToFilter.map((el) => el.id).includes(el.id));
      }
      if (this.personToFilter) {
        proj = proj.filter((el) => el.executors.map((el) => el.id).includes(this.personToFilter.id));
      }
      if (this.date) {
        proj.forEach((el) => {
          el.tasks = [...el.tasks.filter((t) => {
            return (moment(t.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss') >= moment(`${this.date[0]} 00:00:01`, 'yyyy-MM-DD HH:mm:ss')) &&
              (moment(t.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss') <= moment(`${this.date[1]} 23:59:59`, 'yyyy-MM-DD HH:mm:ss'));
          })]
        });
      }
      this.comboItems = proj.map((el) => el.tasks).flat();

      if (this.comboTasks && this.comboTasks.length !== 0) {
        proj.forEach((el) => {
          el.tasks = [...el.tasks.filter((t) => this.comboTasks.map((v) => v.title).includes(t.title))]
        });
      }

      return proj;
    },
    chartData() {
      const labels = this.computedProjectsData.map((el) => el.name);
      return {
        labels,
        datasets: [
          {
            backgroundColor: this.computedProjectsData.map(() => this.randomRGB()),
            data: this.computedProjectsData.map((el) => this.getTaskCount(el)),
          }
        ],
      };
    },
    barChartData() {
      return {
        labels: this.computedProjectsData.map((el) => el.name),
        datasets: [
          {
            label: 'Выполненные задачи',
            backgroundColor: '#41B883',
            data: this.computedProjectsData.map((el) => this.getTasksRange(el)[0])
          },
          {
            label: 'Незавершеные задачи',
            backgroundColor: '#E46651',
            data: this.computedProjectsData.map((el) => this.getTasksRange(el)[1])
          }
        ],
      };
    },
  },
})
</script>

<style scoped>

</style>
