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
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          left
          offset-y
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="date"
              class="mr-2"
              label="Task date"
              prepend-inner-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
              dense
              solo
              hide-details
              style="max-width:140px"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
            no-title
            scrollable
          >
            <v-spacer></v-spacer>
            <v-btn
              text
              color="primary"
              @click="menu = false"
            >
              Cancel
            </v-btn>
            <v-btn
              text
              color="primary"
              @click="$refs.menu.save(date)"
            >
              OK
            </v-btn>
          </v-date-picker>
        </v-menu>

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
        v-for="(project) in $store.state.projects"
        :key="project.id"
        class="mb-4"
      >
        <h3 class="mb-2">{{project.name}}</h3>
        <TaskList
          :date-filter="date"
          :person-filter="personToFilter"
          :project-id="project.id"
          :tasks="project.tasks"
        />
      </div>

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
    };
  },
  methods: {
    headerText() {
      let text = `Все ${this.person.role === 'ROLE_ADMIN' ? '' : 'мои'} проекты`;
      if (this.personToFilter) {
        text += ` с исполнителем ${this.personToFilter.name} ${this.personToFilter.surname}`
      }
      return text;
    },
  },
  computed: mapState({
    person: state => state.user,
  }),
})
</script>

<style scoped>

</style>
