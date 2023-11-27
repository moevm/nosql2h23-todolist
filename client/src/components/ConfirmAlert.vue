<template>
    <v-dialog
        v-model="isAlertShown"
        width="350"
    >
        <v-card align="center">
            <v-icon
                class="fa-fade"
                role="img"
                size="200"
                color="blue-grey-lighten-1"
            >
                mdi-help-circle-outline
            </v-icon>
            <v-card-text class="text-h5 pb-0">
                    {{ message }}
            </v-card-text>
            <v-card-actions class="justify-space-around">
                <v-btn
                    class="text-capitalize"
                    color="blue darken-1"
                    text
                    @click="cancel"
                >
                    Cancel
                </v-btn>
                <v-btn
                    class="text-capitalize"
                    color="green darken-1"
                    text
                    @click="confirm"
                >
                    Confirm
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: "ConfirmAlert",
    data(){
      return {
          message: '',
          isAlertShown: false,
          resolvePromise: undefined,
      }
    },
    methods: {
        show(opts = {}) {
            this.message = opts.message
            this.isAlertShown = true;
            return new Promise((resolve) => {
                this.resolvePromise = resolve
            })
        },
        confirm() {
            this.isAlertShown = false;
            this.resolvePromise(true)
        },

        cancel() {
            this.isAlertShown = false;
            this.resolvePromise(false)
        },
    }
}
</script>
