<template>
  <v-container fluid>
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
        <v-combobox
          v-model="projects"
          :items="projectItems"
          :search-input.sync="search"
          class="mr-2"
          prepend-inner-icon="mdi-magnify"
          clearable
          hide-selected
          multiple
          small-chips
          solo
          dense
          hide-details
          label="Проект"
        />
        <v-combobox
          v-if="$store.state.userRole === 'admin'"
          :items="['Сотрудник 1', 'Сотрудник 2', 'Сотрудник 3']"
          :search-input.sync="search"
          prepend-inner-icon="mdi-magnify"
          clearable
          hide-selected
          multiple
          small-chips
          dense
          hide-details
          solo
          label="Сотрудник"
        />
      </v-col>
    </v-row>
    <v-container
      class="pa-1"
      fluid
    >
      <TaskList />
    </v-container>
  </v-container>
</template>

<script>
import {defineComponent} from 'vue'
import TaskList from '@/components/TaskList.vue';
import FiltersPanel from "@/components/FiltersPanel.vue";

const person = {
  name: "Артем",
  surname: "Смирнов",
  email: "email@gmail.com",
  password: "pass",
  role: "admin",
};

export default defineComponent({
  name: "HomeContent",
  components: {FiltersPanel, TaskList},
  data: () => {
    return {
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      items: ['Смирнов А.В.', 'Началов И.А.', 'Кахоркин М.С.'],
      projects: [],
      projectItems: ['Проект 1', 'Проект 2', 'Проект 3', 'Проект 4'],
      search: '',
      person
    };
  },
})
</script>

<style scoped>

</style>
