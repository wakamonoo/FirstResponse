<template>
    <v-row class="pa-4">
      <v-col cols="12" md="12">
        <DataList></DataList>
      </v-col>
    </v-row>
  </template>

  <script>
  import DataList from '../components/DataUsers.vue'

  export default {
    components: {
      DataList,
    },
    computed: {

    },
    mounted() {
      console.log("Mounted Test")
    }
  }
  </script>
