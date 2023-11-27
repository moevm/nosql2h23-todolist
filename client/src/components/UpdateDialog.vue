<template>
  <v-dialog
    v-model="isDialogShown"
    width="500"
    @click:outside="closeDialog"
    @keydown.esc="closeDialog"
  >
    <v-card>
      <v-card-title class="text-h5">
        Enter task title
      </v-card-title>
      <v-card-text>
        <v-text-field
          label="Task description"
          :rules="rules"
          v-model="taskText"
          hide-details="auto"
          flat
          @change="onInputChange"
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
    <ConfirmAlert ref="confirmUpdateDialogue"/>
  </v-dialog>
</template>

<script>
import ConfirmAlert from "./ConfirmAlert.vue";
import {mapState} from "vuex";

export default {
  name: "UpdateDialog",
  components: {ConfirmAlert},
  props: {
    mutation: {
      type: String,
      required: true,
    },
    fieldToUpdate: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      taskId: '',
      isDialogShown: false,
      taskText: '',
      variables: {input: {id: undefined}}
    }
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    isConfirmButtonDisabled() {
      return !!this.taskText && this.taskText.length >= 3;
    }
  },
  methods: {
    async onSubmit(mutate) {
      const isConfirmed = await this.$refs.confirmUpdateDialogue.show({
        message: "Confirm update?"
      });
      if (isConfirmed) {
        mutate();
        this.closeDialog();
      }
    },
    closeDialog() {
      this.isDialogShown = false;
    },
    openDialog(opts) {
      this.taskText = opts.initialValue;
      this.taskId = opts.taskId;
      this.variables.input.id = this.taskId;
      this.isDialogShown = true;
    },
    onInputChange() {
      this.variables.input[this.fieldToUpdate] = this.taskText;
    }
  }
}
</script>
