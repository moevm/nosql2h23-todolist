<template>
  <v-container fluid>
    <h1>Statistics</h1>
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
    <h2>Соотношение выполненных задач в проектах</h2>
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
      items: ['Проект 1', 'Проект 2', 'Проект 3', 'Проект 4'],
      search: '',
      sortBy: 'name',
      keys: [
        'Сотрудник 1',
        'Сотрудник 2',
        'Сотрудник 3',
        'Сотрудник 4',
        'Сотрудник 5',
        'Сотрудник 6',
      ],
      chartData: {
        labels: ['Проект 1', 'Проект 2', 'Проект 3', 'Проект 4'],
        datasets: [
          {
            backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
            data: [40, 20, 80, 10]
          }
        ],
      },
      barChartData: {
        labels: ['Проект 1', 'Проект 2', 'Проект 3', 'Проект 4'],
        datasets: [
          {
            label: 'Выполненные задачи',
            backgroundColor: '#41B883',
            data: [40, 20, 80, 10]
          },
          {
            label: 'Незавершеные задачи',
            backgroundColor: '#E46651',
            data: [10, 20, 4, 25]
          }
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      },
      personToFilter: {},
    }
  },
})
</script>

<style scoped>

</style>
