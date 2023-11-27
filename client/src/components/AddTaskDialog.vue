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
          >
            <template #append>
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
                    label="Срок выполнения"
                    prepend-inner-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                    filled
                    hide-details
                    style="max-width:160px"
                    dense
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
            </template>
          </v-text-field>
          <v-combobox
            label="Исполнители"
            v-model="newTask"
            :items="['asd']"
            hide-details="auto"
            solo
            multiple
            chips
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

export default {
  name: "AddTaskDialog",
  components: {ConfirmAlert},
  props: {
    opened: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      newTask: '',
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
    }
  },
  computed: {
    ...mapState('validators', {
      rules: state => state.taskInputRules
    }),
    isConfirmButtonDisabled() {
      return !!this.newTask && this.newTask.length >= 3;
    }
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    async onSubmit(mutate) {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmDialogue.show({
        message: "Confirm adding?"
      });
      if (isConfirmed) {
        mutate();
        await this.showAlert({message: 'Task added successfully!'});
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
