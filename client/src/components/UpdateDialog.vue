<template>
  <v-dialog
    v-model="isDialogShown"
    width="500"
    @click:outside="closeDialog"
    @keydown.esc="closeDialog"
  >
    <v-card>
      <v-card-title class="text-h5">
        Редактирование задачи
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

        <span class="label-span">Срок исполнения</span>
        <el-date-picker
          v-model="value.dateOfDeadline"
          class="elevation-2 mb-3"
          style="width: 100%"
          type="datetime"
          placeholder="Срок исполнения, до"
        />

        <span class="label-span">Исполнитель</span>
        <v-combobox
          label="Исполнитель"
          :value="value.executer"
          :item-text="(item) => item.name + ' ' + item.surname"
          item-value="id"
          :items="$store.state.persons"
          class="mb-3"
          hide-details="auto"
          return-object
          solo
          @input="(val) => this.executer = val"
        />

        <v-radio-group
          v-model="value.status"
          row
        >
          <v-radio
            label="Выполнена"
            value="COMPLETE"
          ></v-radio>
          <v-radio
            label="В процессе"
            value="INCOMPLETE"
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
import moment from "moment";

export default {
  name: "UpdateDialog",
  components: {ConfirmAlert},
  props: {
    mutation: {
      type: String,
    },
    fieldToUpdate: {
      type: String,
    },
  },
  inject: ['projectService'],
  data() {
    return {
      value: {},
      isDialogShown: false,
      executer: {},
    }
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    isConfirmButtonDisabled() {
      return !!this.value.title && this.value.dateOfDeadline;
    }
  },
  methods: {
    ...mapActions(['updateTask']),
    async onSubmit() {
      const isConfirmed = await this.$refs.confirmUpdateDialogue.show({
        message: "Сохранить изменения?"
      });
      if (isConfirmed) {
        const valueToSend = {...this.value, executer: {...this.executer}};
        valueToSend.dateOfDeadline = moment(valueToSend.dateOfDeadline).format('DD.MM.yyyy HH:mm:ss');
        this.projectService.editTask(this.$route.params.id, this.value.id, valueToSend).then(() => {
          this.closeDialog();
          this.updateTask(this.$route.params.id);
        })
      }
    },
    closeDialog() {
      this.isDialogShown = false;
    },
    openDialog(opts) {
      this.value = {...opts.task};
      this.executer = {...opts.task.executer};
      this.value.dateOfDeadline = moment(this.value.dateOfDeadline, 'DD.MM.yyyy HH:mm:ss');
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
