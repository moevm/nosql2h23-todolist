<template>
  <v-dialog
    v-model="isDialogShown"
    width="500"
    @click:outside="closeDialog"
    @keydown.esc="closeDialog"
  >
    <v-card>
      <v-card-title class="text-h5">
        Редактирование подзадачи
      </v-card-title>
      <v-card-text>
        <span class="label-span">Описание</span>
        <v-text-field
          label="Описание"
          class="task-input mb-3"
          :rules="rules"
          v-model="value.title"
          hide-details
          dense
          solo
        />

        <v-radio-group
          v-model="value.status"
          row
        >
          <v-radio
            label="Выполнена"
            :value="true"
          ></v-radio>
          <v-radio
            label="В процессе"
            :value="false"
          ></v-radio>
        </v-radio-group>
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
    <ConfirmAlert ref="confirmUpdateDialogue"/>
  </v-dialog>
</template>

<script>
import ConfirmAlert from "./ConfirmAlert.vue";
import {mapActions, mapState} from "vuex";

export default {
  name: "UpdateSubTaskDialog",
  components: {ConfirmAlert},
  inject: ['projectService'],
  data() {
    return {
      value: {},
      isDialogShown: false,
      taskId: null,
    }
  },
  props: {
    projectId: {},
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    isConfirmButtonDisabled() {
      return !!this.value.title;
    }
  },
  methods: {
    ...mapActions(['updateTask']),
    async onSubmit() {
      const isConfirmed = await this.$refs.confirmUpdateDialogue.show({
        message: "Сохранить изменения?"
      });
      if (isConfirmed) {
        const valueToSend = {
          title: this.value.title,
          status: this.value.status,
        };
        this.projectService.editSubtask(this.projectId || this.$route.params.id, this.taskId, this.value.id, valueToSend).then(() => {
          this.closeDialog();
          this.updateTask(this.projectId || this.$route.params.id);
        })
      }
    },
    closeDialog() {
      this.isDialogShown = false;
    },
    openDialog(opts) {
      this.value = {...opts.task};
      this.taskId = opts.taskId;
      this.isDialogShown = true;
    },
  }
}
</script>

<style scoped>
.label-span {
  font-size: 17px;
}
</style>
