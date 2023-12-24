<template>
  <div>
    <v-list class="pa-0" flat >
      <v-list-item-group multiple >
        <transition-group
          name="custom-classes-transition"
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
          :duration="{enter: 500, leave: 500}"
          appear
        >
          <v-list-item
            v-for="task in filteredData(subtasks)"
            :key="task.id"
          >
            <v-list-item-action>
              <v-checkbox
                :disabled="false"
                :input-value="task.status"
                color="success"
                on-icon="mdi-radiobox-marked"
                off-icon="mdi-radiobox-blank"
                @change="(v) => onChecked(v, task)"
              />
            </v-list-item-action>
            <v-list-item-content :class="[completedSubTaskClass(task.status)]">
              <v-list-item-title>{{ task.title }}</v-list-item-title>
            </v-list-item-content>
            <v-list-item-icon class="ma-1">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    @click="openUpdateDialog(task)"
                    v-bind="attrs"
                    v-on="on"
                    icon
                  >
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                </template>
                <span>Update</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    :disabled="false"
                    @click="onDelete('Подзадача успешно удалена!', task)"
                    v-bind="attrs"
                    v-on="on"
                    icon
                  >
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                </template>
                <span>Delete</span>
              </v-tooltip>
            </v-list-item-icon>
          </v-list-item>
        </transition-group>
      </v-list-item-group>
      <v-divider/>
      <transition
        name="custom-classes-transition"
        enter-active-class="animated fadeIn"
        appear
      >
        <v-list-item>
          <v-list-item-content>
            <v-text-field
              :color="inputStateColor"
              label="Описание подзадачи"
              v-model="newSubTask"
              hide-details="auto"
              flat
            >
              <template v-slot:append>
                <v-icon
                  @click="onSubmit()"
                  :disabled="!isInputValid"
                  :color="inputStateColor"
                >
                  mdi-plus
                </v-icon>
              </template>
            </v-text-field>
          </v-list-item-content>
        </v-list-item>
      </transition>
    </v-list>
    <ConfirmAlert ref="confirmDeletingSubTaskAlert"/>
    <UpdateSubTaskDialog
      ref="updateSubTaskDialogue"
    />
  </div>
</template>
<script>
import ConfirmAlert from "./ConfirmAlert.vue";

import {mapActions, mapState} from "vuex";
import UpdateSubTaskDialog from "@/components/UpdateSubTaskDialog.vue";

export default {
  name: "SubTask",
  components: {UpdateSubTaskDialog, ConfirmAlert},
  props: {
    taskId: {
      type: String,
      required: true
    },
    subtasks: {
      type: Array,
      default: () => [],
    },
    executerId: {
      type: String,
    },
  },
  inject: ['projectService'],
  data() {
    return {
      newSubTask: '',
    }
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    ...mapActions(['addSubTask', 'updateSubTask']),
    async onSubmit() {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmDeletingSubTaskAlert.show({
        message: "Создать подзадачу?"
      });
      if (isConfirmed) {
        const newSubTask = {
          title: this.newSubTask,
        };
        this.projectService.addNewSubtask(this.$route.params.id, this.taskId, newSubTask).then(() => {
          this.addSubTask(this.$route.params.id);
          this.newSubTask = '';
        }).catch(() => this.showAlert({message: 'Не удалось создать подзадачу', type: 'error'}))
      }
    },
    async onDelete(message, task) {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmDeletingSubTaskAlert.show({
        message: "Удалить подзадачу?"
      });
      if (isConfirmed) {
        this.projectService.removeSubtask(this.$route.params.id, this.taskId, task.id).then(() => {
          this.updateSubTask(this.$route.params.id);
          this.showAlert({message: message})
        }).catch(() => this.showAlert({message: 'Не удалось удалить подзадачу', type: 'error'}))
      }
    },
    filteredData(data) {
      if (this.currentFilter.name === 'all') {
        return data;
      } else if (this.currentFilter.name === 'complete') {
        return data.filter((task) => task.completed === true);
      } else {
        return data.filter((task) => task.completed === false);
      }
    },
    openUpdateDialog(task) {
      this.$refs.updateSubTaskDialogue.openDialog({
        task,
        taskId: this.taskId,
      })
    },
    onChecked(v, task) {
      const editedSubtask = {
        status: v,
        title: task.title
      };
      this.projectService.editSubtask(this.$route.params.id, this.taskId, task.id,  editedSubtask).then(() => {
        this.updateSubTask(this.$route.params.id);
      }).catch((e) => console.error(e));
    },
  },
  computed: {
    ...mapState(['currentFilter']),
    isInputValid() {
      return !!this.newSubTask && this.newSubTask.length >= 3;
    },
    completedSubTaskClass: function () {
      return function (isChecked) {
        return isChecked ? 'text-decoration-line-through' : '';
      };
    },
    inputStateColor() {
      return !this.isInputValid ? 'grey' : 'green';
    },
  }
}
</script>

<style scoped>
.v-list-item {
  height: 48px;
}
</style>
