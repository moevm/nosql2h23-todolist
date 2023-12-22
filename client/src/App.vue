<template>
  <v-app>
    <router-view/>
    <SuccessAlert/>
  </v-app>
</template>

<script>
import projectService from '@/logic/services/projectService';
import SuccessAlert from "@/components/SuccessAlert.vue";

export default {
  name: 'App',
  components: {SuccessAlert},
  provide: {
    projectService,
  }
};
</script>

<style>
/*Disable Scroll Bar in full-page desktop app*/
html {
  overflow-y: auto;
}

.v-alert {
  position: fixed !important;
  right: 10px;
  top: 4px;
  z-index: 999;
}

.label-span {
  font-size: 16px;
}
</style>
