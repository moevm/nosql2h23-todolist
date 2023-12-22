<template>
  <v-container class="fill-height">
    <v-dialog v-model="dialog" persistent max-width="600px" min-width="360px">
      <div>
        <v-tabs v-model="tab" show-arrows background-color="deep-purple accent-4" icons-and-text dark grow>
          <v-tabs-slider color="purple darken-4"></v-tabs-slider>
          <v-tab v-for="i in tabs" :key="i.name">
            <v-icon large>{{ i.icon }}</v-icon>
            <div class="caption py-1">{{ i.name }}</div>
          </v-tab>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="loginForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field v-model="loginEmail" :rules="loginEmailRules" label="E-mail"
                                    required autocomplete="username"/>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="loginPassword" :append-icon="show1?'eye':'eye-off'"
                                    :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'"
                                    name="input-10-1" label="Password" hint="At least 3 characters" counter
                                    @click:append="show1 = !show1" autocomplete="password"/>
                    </v-col>
                    <v-col class="d-flex" cols="12" sm="6" xsm="12">
                    </v-col>
                    <v-spacer/>
                    <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                      <!--                      <v-btn x-large block :disabled="!valid" color="success" @click="validate"> Login </v-btn>-->
                      <v-btn
                        x-large
                        block
                        color="success"
                        @click="validate"
                        :loading="isLoading"
                      >
                        Login
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="registerForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="firstName" :rules="[rules.required]" label="First Name" maxlength="20"
                                    required/>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="lastName" :rules="[rules.required]" label="Last Name" maxlength="20"
                                    required/>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="email" :rules="emailRules" label="E-mail" required autocomplete="off"/>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'"
                                    name="input-10-1" label="Password" hint="At least 3 characters" counter
                                    @click:append="show1 = !show1" autocomplete="off"/>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field block v-model="verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, passwordMatch]" :type="show1 ? 'text' : 'password'"
                                    name="input-10-1" label="Confirm Password" counter
                                    @click:append="show1 = !show1"/>
                    </v-col>
                    <v-spacer/>
                    <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
                      <v-btn
                        x-large
                        block
                        :disabled="!valid"
                        color="success"
                        @click="validate"
                        :loading="isLoading"
                      >
                        Register
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </v-dialog>
  </v-container>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: 'HomeView',
  inject: ['projectService'],
  data: () => ({
    dialog: true,
    tab: 0,
    tabs: [
      {name: "Login", icon: "mdi-account"},
      {name: "Register", icon: "mdi-account-outline"}
    ],
    valid: true,
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    verify: "",
    loginPassword: "",
    loginEmail: "",
    isLoading: false,
    loginEmailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    emailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],

    show1: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 3) || "Min 3 characters"
    }
  }),
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Password must match";
    }
  },
  methods: {
    ...mapMutations(['setUser']),
    async validate() {
      if (this.$refs.loginForm.validate()) {
        const loginInfo = {
          username: this.loginEmail,
          password: this.loginPassword,
        }
        this.isLoading = true;
        this.projectService.login(loginInfo).then((res) => {
          localStorage.setItem('user', res['jwt-token']);
          this.setUser(res.user);
          this.$router.push({name: 'home'});
        }).catch((e) => {
          console.error('Error: ', e);
        }).finally(() => {
          this.isLoading = false;
        });
        // const user = {
        //   name: "Артем",
        //   surname: "Смирнов",
        //   email: "aschiam.net@gmail.com",
        //   password: "pass",
        //   role: "admin",
        // };
        // const tempPromise = new Promise((resolve, reject) => {
        //   //fetch server
        //   setTimeout(() => {
        //     if (!loginInfo) reject(loginInfo);
        //     resolve(user);
        //   }, 2000);
        // });
        // this.isLoading = true;
        // await tempPromise.then((res) => {
        //   localStorage.setItem('user', JSON.stringify(res));
        //   this.setUser(user);
        // }).finally(() => {
        //   this.isLoading = false;
        //   this.$router.push({name: 'home'});
        // })
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
}
</script>

<style scoped>
.remove-bg {
  mix-blend-mode: multiply;
}
</style>
