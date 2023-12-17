<template>
  <v-app>
    <router-view/>
  </v-app>
</template>

<script>
import projectService from '@/logic/services/projectService';

export default {
  name: 'App',
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
</style>
