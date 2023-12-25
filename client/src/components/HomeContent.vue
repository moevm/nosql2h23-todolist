<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="d-flex flex-row pb-0">
        <h1>{{ person.name + ' ' + person.surname }}</h1>
        <v-icon v-if="$store.state.userRole === 'admin'" class="ml-2">mdi-shield-crown-outline</v-icon>
      </v-col>
      <v-col class="pt-0">
        <span>{{ person.email }}</span>
      </v-col>
    </v-row>
    <v-row
      class="mb-2 pl-0"
    >
      <v-col class="d-flex flex-row align-center" cols="12">
        <FiltersPanel/>
        <v-spacer/>
        <v-text-field
          v-model="taskSearch"
          placeholder="Название задачи"
          class="mr-2"
          clearable
          dense
          hide-details
          solo
        />
        <el-date-picker
          class="mr-2 elevation-1"
          v-model="date"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="ПО"
          value-format="yyyy-MM-dd"
          start-placeholder="С"
          end-placeholder="ДО"
        />

        <v-select
          v-if="$store.state.userRole === 'admin'"
          v-model="personToFilter"
          :items="$store.state.persons"
          :search-input.sync="search"
          :item-text="(item) => item.name + ' ' + item.surname"
          item-value="id"
          return-object
          prepend-inner-icon="mdi-magnify"
          clearable
          hide-selected
          dense
          hide-details
          solo
          label="Исполнитель"
        />
      </v-col>
    </v-row>
    <v-container
      class="pa-1"
      fluid
    >
      <h2 class="mb-2">{{headerText()}}</h2>
      <div
        v-for="(project) in projects"
        :key="project.id"
        class="mb-4"
      >
        <h3 class="mb-2">{{project.name}}</h3>
        <TaskList
          :date-filter="date"
          :person-filter="personToFilter"
          :project-id="project.id"
          :tasks="project.tasks"
          :search-filter="taskSearch"
        />
      </div>
      <v-row class="d-flex justify-center">
        <v-col class="d-flex flex-row align-center justify-center">
          <v-pagination
            v-model="page"
            :disabled="loading"
            :length="Math.ceil($store.state.projects.length / this.size)"
            @input="onLoad"
          ></v-pagination>
          <span>Проектов на странице: </span>
          <v-select
            class="ml-2"
            v-model="size"
            :items="cItems"
            style="max-width: 100px"
            hide-details
            dense
            label="Проектов на странице"
            item-text="label"
            item-value="value"
            @input="onLoad"
            solo
          />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import {defineComponent} from 'vue'
import TaskList from '@/components/TaskList.vue';
import FiltersPanel from "@/components/FiltersPanel.vue";
import {mapState} from "vuex";

export default defineComponent({
  name: "HomeContent",
  components: {FiltersPanel, TaskList},
  data: () => {
    return {
      date: null,
      menu: false,
      projects: [],
      search: '',
      personToFilter: null,
      taskSearch: null,
      page: 1,
      loading: false,
      size: 3,
      cItems: [{
        label: '1',
        value: 1,
        },
        {
          label: '3',
          value: 3,
        },
        {
          label: '5',
          value: 5,
        },
        {
          label: 'Все',
          value: 999,
        }]
    };
  },
  inject: ['projectService'],
  mounted() {
    this.projectService.getAllProjectsAtPage(this.page - 1, this.size).then((res) => {
      this.projects = res;
    }).catch((e) => console.error(e));
  },
  methods: {
    headerText() {
      let text = `Все ${this.person.role === 'ROLE_ADMIN' ? '' : 'мои'} проекты`;
      if (this.personToFilter) {
        text += ` с исполнителем ${this.personToFilter.name} ${this.personToFilter.surname}`
      }
      return text;
    },
    onLoad() {
      this.loading = true;
      this.projectService.getAllProjectsAtPage(this.page - 1, this.size).then((res) => {
        this.projects = res;
      }).catch((e) => console.error(e)).finally(() => {
        this.loading = false
      });
    }
  },
  computed: mapState({
    person: state => state.user,
  }),
})
</script>

<style scoped>

</style>
