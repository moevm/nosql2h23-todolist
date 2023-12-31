<template>
  <v-container
    class="container"
  >
    <div class="d-flex flex-row pl-2 mb-3 align-center">
      <h1>{{ project.name }}</h1>
      <v-tooltip v-if="$store.state.userRole === 'admin'" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" icon text @click="onEdit"><v-icon>mdi-pencil</v-icon></v-btn>
        </template>
        Редактировать проект
      </v-tooltip>
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
            v-for="(user, index) in project.executors"
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
      <v-tooltip v-if="$store.state.userRole === 'admin'" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" icon text @click="removeProject('Проект успешно удален')"><v-icon>mdi-delete</v-icon></v-btn>
        </template>
        Удалить проект
      </v-tooltip>
    </div>
    <v-row
      v-if="$store.state.userRole === 'admin'"
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
        Добавить задачу
      </v-btn>
      <AddTaskDialog :project-id="this.$route.params.id" :opened.sync="isAddTaskDialogShown"/>
    </v-row>
    <v-row
      class="mb-2 pl-2"
    >
      <v-col class="d-flex flex-row align-center pt-0" cols="12">
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
        >
        </el-date-picker>
        <v-select
          v-if="$store.state.userRole === 'admin'"
          v-model="personToFilter"
          :items="$store.state.persons"
          item-value="id"
          :item-text="(item) => item.name + ' ' + item.surname"
          label="Исполнитель"
          return-object
          dense
          solo
          hide-details
          style="max-width:300px"
          clearable
        />
      </v-col>
    </v-row>
    <v-container
      class="pa-1"
      fluid
    >
      <TaskList :person-filter="personToFilter?.id" :date-filter="date" :search-filter="taskSearch"/>
    </v-container>
    <ConfirmAlert ref="confirmMainTaskAddDialogue"/>
    <UpdateProjectDialog ref="updateDialog"/>
  </v-container>
</template>

<script>
import TaskList from "../components/TaskList.vue";
import AddTaskDialog from "../components/AddTaskDialog.vue";
import FiltersPanel from "@/components/FiltersPanel.vue";
import ConfirmAlert from "@/components/ConfirmAlert.vue";
import {mapActions, mapMutations, mapState} from "vuex";
import store from "@/store";
import UpdateProjectDialog from "@/components/UpdateProjectDialog.vue";

export default {
  name: "ProjectView",
  components: {UpdateProjectDialog, ConfirmAlert, FiltersPanel, AddTaskDialog, TaskList},
  inject: ['projectService'],
  data() {
    return {
      date: null,
      menu: false,
      items: ['Смирнов А.В.', 'Началов И.А.', 'Кахоркин М.С.'],
      isAddTaskDialogShown: false,
      taskSearch: null,
      executers: [
        {
          name: 'Артем',
          surname: 'Смирнов',
        },
        {
          name: 'Иван',
          surname: 'Иванов',
        }
      ],
      personToFilter: {},
    }
  },
  computed: {
    ...mapState({
      project: state => state.activeProject,
    })
  },
  beforeRouteEnter: async (to, from, next) => {
    await store.dispatch('getActiveProject', to.params.id);
    next()
  },
  beforeRouteUpdate: async (to, from, next) => {
    await store.dispatch('getActiveProject', to.params.id);
    next()
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    ...mapActions(['getActiveProject']),
    ...mapMutations(['deleteProject']),
    async removeProject(alertMessage) {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmMainTaskAddDialogue.show({
        message: "Are you sure?"
      });
      if (isConfirmed) {
        this.projectService.deleteProject(this.$route.params.id).catch((e) => console.error(e));
        this.deleteProject(this.project.id);
        this.$router.push({ name: 'home' });
        await this.showAlert({message: alertMessage});
      }
    },
    onEdit() {
      this.$refs.updateDialog.openDialog({
        project: this.project,
      })
    },
  }
}
</script>

<style scoped>
.container {
  max-width: 1920px;
}


</style>

<style>
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
