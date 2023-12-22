<template>
  <v-layout>
    <v-dialog
      :value="opened"
      width="500"
      @click:outside="closeDialog"
      @keydown.esc="closeDialog"
    >
      <v-card>
        <v-card-title class="text-h5">
          Создать новую задачу
        </v-card-title>
        <v-card-text>
          <v-text-field
            label="Task description"
            class="task-input mb-2"
            :rules="rules"
            v-model="newTask"
            hide-details="auto"
            dense
            solo
          />
          <el-date-picker
            v-model="date"
            class="elevation-2 mb-2"
            style="width: 100%"
            type="datetime"
            placeholder="Срок исполнения, до"
          />

          <v-combobox
            label="Исполнитель"
            v-model="executerId"
            :items="['asd']"
            hide-details="auto"
            solo
          />
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn
            class="text-capitalize"
            color="green darken-1"
            text
            @click="closeDialog"
          >
            Cancel
          </v-btn>
          <v-btn
            class="text-capitalize"
            color="green darken-1"
            text
            :disabled="!isConfirmButtonDisabled"
            @click="onSubmit()"
          >
            Submit
          </v-btn>
        </v-card-actions>
      </v-card>
      <ConfirmAlert ref="confirmDialogue"/>
    </v-dialog>
  </v-layout>
</template>

<script>
import ConfirmAlert from "./ConfirmAlert.vue";
import {mapActions, mapState} from "vuex";
import moment from "moment";

export default {
  name: "AddTaskDialog",
  components: {ConfirmAlert},
  props: {
    opened: {
      type: Boolean,
      required: true
    }
  },
  inject: ['projectService'],
  data() {
    return {
      newTask: '',
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      executerId: [],
    }
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    ...mapState(['user']),
    isConfirmButtonDisabled() {
      return !!this.newTask && this.newTask.length >= 3;
    }
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    ...mapActions(['addTask']),
    async onSubmit() {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmDialogue.show({
        message: "Добавить задачу?"
      });
      if (isConfirmed) {
        const newTask = {
          title: this.newTask,
          executerId: this.user.id,
          dateOfDeadline: moment(this.date).format('DD.MM.yyyy HH:mm:ss'),
        }
        this.projectService.addNewTask(this.$route.params.id, newTask).then((res) => {
          console.log(res);
          this.addTask(res);
        }).catch((e) => console.log(e));
        await this.showAlert({message: 'Задача успешно создана!'});
        this.$emit('update:opened', false);
      }
    },
    closeDialog() {
      this.$emit('update:opened', false);
    },
  }
}
</script>

<style>
.task-input .v-input__slot {
  padding-right: 0 !important;
}
</style>
