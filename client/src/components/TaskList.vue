<template>
  <div>
    <v-card class="mx-auto">
      <v-card-actions>
        <v-row>
          <v-expansion-panels>
            <v-expansion-panel v-if="filteredTasks.length === 0">
              <v-expansion-panel-header>
                <span>Список задач пуст</span>
              </v-expansion-panel-header>
            </v-expansion-panel>
            <v-expansion-panel
              v-for="(task, index) in filteredTasks"
              :key="task.id"
            >
              <v-expansion-panel-header class="pa-2" disable-icon-rotate>
                <v-chip :color="deadlineColor(task.dateOfDeadline)" small style="max-width: min-content; opacity: 0.9" class="mr-3">
                  {{ task.dateOfDeadline }}
                </v-chip>
                {{ index + 1 }}. {{ task.title }}
                <template v-slot:actions>
                  <v-speed-dial
                    direction="left"
                    :open-on-hover="true"
                    transition="slide-x-transition"
                  >
                    <template v-slot:activator>
                      <v-btn
                        :value="false"
                        @click.native.stop=""
                        color="blue darken-2"
                        dark
                        fab
                        icon
                        small
                      >
                        <v-icon>
                          mdi-dots-horizontal
                        </v-icon>
                      </v-btn>
                    </template>
                    <v-tooltip content-class="executers-tooltip" left>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          v-bind="attrs"
                          v-on="on"
                          fab
                          dark
                          small
                          color="blue"
                          @click.native.stop
                        >
                          <v-icon>mdi-information</v-icon>
                        </v-btn>
                      </template>
                      <v-list class="executers-list" dense>
                        <v-row>
                          <v-col class="ml-4"><span>Исполнитель: </span></v-col>
                        </v-row>
                        <v-divider/>
                        <v-list-item
                          class="pl-0"
                        >
                          <v-list-item-icon class="mr-2 ml-2">
                            <v-avatar
                              class="white--text"
                              color="primary"
                              size="25"
                            >{{ task.executer.name[0] + task.executer.surname[0] }}
                            </v-avatar>
                          </v-list-item-icon>
                          {{ task.executer.name + ' ' + task.executer.surname }}
                        </v-list-item>
                      </v-list>
                    </v-tooltip>
                    <v-tooltip v-if="$store.state.userRole === 'admin'" bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          v-bind="attrs"
                          v-on="on"
                          fab
                          dark
                          small
                          color="green"
                          @click.native.stop="openUpdateDialog(task)"
                        >
                          <v-icon>mdi-pencil</v-icon>
                        </v-btn>
                      </template>
                      <span>Редактировать</span>
                    </v-tooltip>

                    <v-tooltip v-if="$store.state.userRole === 'admin'" bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          v-bind="attrs"
                          v-on="on"
                          fab
                          dark
                          small
                          color="red"
                          @click.native.stop="onTaskDelete('Задача успешно удалена!', task.id)"
                        >
                          <v-icon>mdi-delete</v-icon>
                        </v-btn>
                      </template>
                      <span>Удалить</span>
                    </v-tooltip>
                  </v-speed-dial>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content class="subtasks-container">
                <SubTaskList
                  :task-id="task.id"
                  :subtasks="task.subtasks"
                  :executer-id="task.executer.id"
                  :project-id="projectId"
                />
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-row>
      </v-card-actions>

    </v-card>
    <v-sheet
      v-if="false"
      :color="'grey'"
      class="pa-3 mx-auto"
    >
      <v-skeleton-loader
        class="mx-auto"
        type="card"
      />
    </v-sheet>
    <ConfirmAlert ref="confirmDialogue"/>
    <UpdateDialog
      ref="updateDialogue"
      :mutation="''"
      :project-id="projectId"
    />
  </div>
</template>
<script>
import SubTaskList from "./SubTaskList.vue";

import {mapActions, mapGetters, mapState} from 'vuex';
import ConfirmAlert from "@/components/ConfirmAlert.vue";
import UpdateDialog from "@/components/UpdateDialog.vue";
import moment from "moment";

export default {
  name: "TaskList",
  data() {
    return {

    }
  },
  props: ['personFilter', 'dateFilter', 'projectId', 'tasks', 'searchFilter'],
  inject: ['projectService'],
  computed: {
    ...mapState(['currentFilter']),
    ...mapGetters(['project']),
    filteredTasks: function () {
      let filtered = this.tasks || this.project.tasks || [];
      if (this.personFilter) {
        filtered = filtered.filter((el) => el.executer.id === this.personFilter);
      }
      if (this.dateFilter && Array.isArray(this.dateFilter)) {
        filtered = filtered.filter((el) => {
          return (moment(el.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss') >= moment(`${this.dateFilter[0]} 00:00:01`, 'yyyy-MM-DD HH:mm:ss')) &&
            (moment(el.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss') <= moment(`${this.dateFilter[1]} 23:59:59`, 'yyyy-MM-DD HH:mm:ss'))
        });
      } else if (this.dateFilter && !Array.isArray(this.dateFilter)) {
        filtered = filtered.filter((el) => moment(el.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss') <= moment(`${this.dateFilter} 23:59:59`, 'yyyy-MM-DD HH:mm:ss'));
      }
      if (this.currentFilter.name === 'COMPLETE') {
        filtered = filtered.filter((task) => task.status === 'COMPLETE');
      }
      if (this.currentFilter.name === 'INCOMPLETE') {
        filtered = filtered.filter((task) => task.status === 'INCOMPLETE');
      }
      if (this.searchFilter) {
        filtered = filtered.filter((task) => task.title.includes(this.searchFilter));
      }
      return filtered;
    },
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    ...mapActions(['removeTask']),
    deadlineColor(date) {
      const m = moment(date, 'DD.MM.yyyy HH:mm:ss');
      if (moment().diff(moment(date, 'DD.MM.yyyy HH:mm:ss'), 'days') === 0) return 'orange lighten-3';
      return m > moment() ? 'green lighten-3' : 'red lighten-3';
    },
    async onTaskDelete(alertMessage, task_id) {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmDialogue.show({
        message: "Удалить задачу?"
      });
      if (isConfirmed) {
        this.projectService.removeTask(this.projectId || this.$route.params.id, task_id).then(() => {
          this.removeTask(task_id);
        }).catch(() => this.showAlert({message: 'Не удалось удалить задачу.'}));
        await this.showAlert({message: alertMessage});
      }
    },
    openUpdateDialog(task) {
      this.$refs.updateDialogue.openDialog({
        task,
      })
    },
  },
  components: {UpdateDialog, ConfirmAlert, SubTaskList}
}
</script>

<style>
::v-deep .subtasks-container > .v-expansion-panel-content__wrap {
  padding: 0 0 15px 0;
}
</style>
