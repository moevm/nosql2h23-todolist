<template>
  <v-container
    class="container"
    fluid
  >
    <div class="d-flex flex-row pl-2 mb-1">
      <h1>{{ $route.params.projectName }}</h1>
      <v-spacer/>
      <v-tooltip content-class="executers-tooltip" left>
        <template v-slot:activator="{ on, attrs }">
          <v-icon v-bind="attrs" v-on="on">mdi-information</v-icon>
        </template>
        <v-list class="executers-list" dense>
          <v-row>
            <v-col class="ml-4"><span>Исполнители:</span></v-col>
          </v-row>
          <v-divider/>
          <v-list-item
            class="pl-0"
            v-for="(user, index) in executers"
            :key="index"
          >
            <v-list-item-icon class="mr-2 ml-2">
              <v-avatar
                class="white--text"
                color="primary"
                size="25"
              >{{ user.name[0] + user.surname[0] }}
              </v-avatar>
            </v-list-item-icon>
            {{ user.name + ' ' + user.surname }}
          </v-list-item>
        </v-list>
      </v-tooltip>
    </div>
    <v-row
      class="pl-2"
      no-gutters
    >
      <v-btn
        class="pa-2 text-capitalize"
        text
        @click="isAddTaskDialogShown = !isAddTaskDialogShown"
      >
        <v-icon
          left
          color="red"
        >
          mdi-plus
        </v-icon>
        Add task
      </v-btn>
      <AddTaskDialog :opened.sync="isAddTaskDialogShown"/>
    </v-row>
    <v-row
      class="mb-2 pl-2"
    >
      <v-col class="d-flex flex-row align-center pt-0" cols="12">
        <FiltersPanel/>
        <v-spacer/>
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          left
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="date"
              label="Task date"
              prepend-inner-icon="mdi-calendar"
              class="mr-2"
              readonly
              v-bind="attrs"
              v-on="on"
              dense
              hide-details
              solo
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
          :items="items"
          label="Сотрудник"
          dense
          solo
          hide-details
          style="max-width:300px"
        />
      </v-col>
    </v-row>
    <v-container
      class="pa-1"
      fluid
    >
      <TaskList />
    </v-container>
    <SuccessAlert/>
  </v-container>
</template>

<script>
import TaskList from "../components/TaskList.vue";
import AddTaskDialog from "../components/AddTaskDialog.vue";
import SuccessAlert from "../components/SuccessAlert.vue";
import FiltersPanel from "@/components/FiltersPanel.vue";

export default {
  name: "ProjectView",
  components: {FiltersPanel, SuccessAlert, AddTaskDialog, TaskList},
  data() {
    return {
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      items: ['Смирнов А.В.', 'Началов И.А.', 'Кахоркин М.С.'],
      isAddTaskDialogShown: false,
      executers: [
        {
          name: 'Артем',
          surname: 'Смирнов',
        },
        {
          name: 'Иван',
          surname: 'Иванов',
        }
      ]
    }
  },
}
</script>

<style scoped>
.container {
  max-width: 1920px;
}

.executers-tooltip {
  opacity: 1;
  padding: 0;
  box-shadow: 0 0 3px black;
  border-radius: 5px;
}

.executers-list {
  border-radius: 6px;
}
</style>
