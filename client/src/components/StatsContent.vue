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
    </v-row>
    <h2>Соотношение количества задач в проектах</h2>
    <v-row>
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
            :chart-options="chartOptions"
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
          height: '400px'
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
      projectsToFilter: [],
      personToFilter: null,
      search: '',
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
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
    }
  },
  computed: {
    ...mapState(['projects', 'persons']),
    computedProjectsData() {
      let proj = [...this.projects];
      if (this.projectsToFilter.length !== 0) {
        proj = proj.filter((el) => this.projectsToFilter.map((el) => el.id).includes(el.id));
      }
      if (this.personToFilter) {
        proj = proj.filter((el) => el.executors.map((el) => el.id).includes(this.projectsToFilter.id));
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
