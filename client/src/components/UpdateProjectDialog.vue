<template>
  <v-dialog
    v-model="isDialogShown"
    width="500"
    @click:outside="closeDialog"
    @keydown.esc="closeDialog"
  >
    <v-card>
      <v-card-title class="text-h5">
        Редактирование проекта
      </v-card-title>
      <v-card-text>
        <span class="label-span">Название</span>
        <v-text-field
          label="Название проекта"
          class="task-input mb-3"
          :rules="rules"
          v-model="value.name"
          hide-details
          dense
          solo
        />

        <v-radio-group
          v-model="value.status"
          row
        >
          <v-radio
            label="Завершен"
            :value="'DONE'"
          ></v-radio>
          <v-radio
            label="В процессе"
            :value="'IN_PROGRESS'"
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
  name: "UpdateProjectDialog",
  components: {ConfirmAlert},
  inject: ['projectService'],
  data() {
    return {
      value: {},
      isDialogShown: false,
      statusItems: [{
        label: 'Завершен',
        value: 'DONE',
      }, {
        label: 'В процессе',
        value: 'IN_PROGRESS',
      }]
    }
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    isConfirmButtonDisabled() {
      return !!this.value.name;
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
          name: this.value.name,
          status: this.value.status,
        };
        this.projectService.editProject(this.$route.params.id, valueToSend).then(() => {
          this.closeDialog();
          this.updateTask(this.$route.params.id);
        })
      }
    },
    closeDialog() {
      this.isDialogShown = false;
    },
    openDialog(opts) {
      this.value = {...opts.project};
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
