<template>
  <v-snackbar
    :color="color"
    :value="visible"
    :timeout="timeout"
    min-width="100px"
    top
    right
  >
    <v-icon v-if="type === 'success'" class="mr-2">mdi-check</v-icon>
    {{ message }}
    <template v-slot:action>
<!--      <v-btn-->
<!--        :disabled="false"-->
<!--        @click="onUndo()"-->
<!--        icon-->
<!--      >-->
<!--        <v-icon>mdi-arrow-u-left-top</v-icon>-->
<!--      </v-btn>-->
      <v-btn
        icon
        @click="hideAlert()"
      >
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
import {mapActions, mapState} from 'vuex';

export default {
  name: "SuccessAlert",
  computed: {
    ...mapState('alert', {
      message: state => state.alertMessage,
      visible: state => state.isVisible,
      timeout: state => state.timeoutValue,
      type: state => state.alertType,
    }),
    color() {
      return this.type === 'success' ? 'green' : 'red';
    }
  },
  methods: {
    ...mapActions('alert', {hideAlert: 'hideAlert'}),
    // onUndo(mutate) {
    //   mutate();
    //   this.hideAlert();
    // }
  }
}
</script>
