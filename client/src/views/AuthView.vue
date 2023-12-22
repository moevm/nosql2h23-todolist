<template>
  <v-container class="fill-height align-center">
    <v-app-bar
      app
      clipped-left
      style="z-index: 200"
    >
      <v-app-bar-title>TODO App</v-app-bar-title>
      <v-spacer/>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <div v-bind="attrs"
               v-on="on"
          >
            <v-switch
              v-model="$vuetify.theme.dark"
              inset
              prepend-icon="mdi-theme-light-dark"
              hide-details="true"
            />
          </div>
        </template>
        <span>Сменить тему на {{$vuetify.theme.dark ? 'светлую' : 'темную'}}</span>
      </v-tooltip>
    </v-app-bar>

    <v-main class="fill-height">
      <v-container class="d-flex fill-height justify-center">
        <v-card max-width="600">
          <v-tabs
            v-model="tab"
            show-arrows
            icons-and-text
            grow
          >
            <v-tab v-for="i in tabs" :key="i.name">
              <v-icon>{{ i.icon }}</v-icon>
              <div class="caption py-1">{{ i.name }}</div>
            </v-tab>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form
                    ref="loginForm"
                    v-model="valid"
                    autocomplete="off"
                  >
                    <v-row>
                      <v-col cols="12">
                        <span class="label-span">Логин</span>
                        <v-text-field
                          v-model="loginEmail"
                          :rules="loginEmailRules"
                          class="mt-2"
                          placeholder="email@gmail.com"
                          required
                          solo
                        />
                      </v-col>
                      <v-col cols="12">
                        <span class="label-span">Пароль</span>
                        <v-text-field
                          v-model="loginPassword"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min]"
                          :type="show1 ? 'text' : 'password'"
                          class="mt-2"
                          hint="At least 3 characters"
                          counter
                          solo
                          @click:append="show1 = !show1"
                        />
                      </v-col>
                      <v-col class="d-flex" cols="12" sm="6" xsm="12">
                      </v-col>
                      <v-spacer/>
                      <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                        <v-btn
                          large
                          block
                          color="green lighten-5"
                          @click="validate"
                          :loading="isLoading"
                        >
                          Войти
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
                  <v-form
                    ref="registerForm"
                    v-model="valid"
                  >
                    <v-row>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          v-model="firstName"
                          :rules="[rules.required]"
                          label="First Name"
                          maxlength="20"
                          required
                          solo
                        />
                      </v-col>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          v-model="lastName"
                          :rules="[rules.required]"
                          label="Last Name"
                          maxlength="20"
                          required
                          solo
                        />
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="email"
                          :rules="emailRules"
                          label="E-mail"
                          required
                          solo
                        />
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          v-model="password"
                          :type="show1 ? 'text' : 'password'"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min]"
                          label="Password"
                          hint="At least 3 characters"
                          counter
                          solo
                          @click:append="show1 = !show1"
                        />
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          block
                          v-model="verify"
                          :type="show1 ? 'text' : 'password'"
                          autocomplete="new-password"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, passwordMatch]"
                          label="Confirm Password"
                          counter
                          solo
                          @click:append="show1 = !show1"
                        />
                      </v-col>
                      <v-spacer/>
                      <v-col class="d-flex justify-end" cols="12">
                        <v-btn
                          large
                          :disabled="!valid"
                          color="green lighten-5"
                          @click="validateRegister"
                          :loading="isLoading"
                        >
                          Зарегистрироваться
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-card>

      </v-container>
    </v-main>
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
      }
    },
    async validateRegister() {
      if (this.$refs.registerForm.validate()) {
        const regInfo = {
          name: this.firstName,
          surname: this.lastName,
          email: this.email,
          password: this.loginPassword,
        }
        this.isLoading = true;
        this.projectService.register(regInfo).then((res) => {
          localStorage.setItem('user', res['jwt-token']);
          this.setUser(res.user);
          this.$router.push({name: 'home'});
        }).catch((e) => {
          console.error('Error: ', e);
        }).finally(() => {
          this.isLoading = false;
        });
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

<style>

</style>