<template>
    <v-app-bar elevation="0" app color="#337ab7" class="py-2 bg-blue">
      <v-container>
        <v-row>
          <v-col cols="6" lg="2" class="d-flex align-center px-2 px-md-0">
            <a href="/" aria-label="Go to Homepage">
              <v-img
                :src="logo"
                :width="smAndUp ? 300 : 250"
                height="auto"
                aspect-ratio="16/9"
                contain

              ></v-img>
            </a>
          </v-col>
          <v-col cols="6" class="d-flex d-lg-none justify-end align-center">
            <v-btn icon @click="dialog = true">
              <v-icon size="25">mdi-menu</v-icon>
            </v-btn>
          </v-col>
          <!-- Default View -->
          <v-col
            cols="12"
            lg="10"
            class="d-none d-lg-flex justify-end align-center"
          >
            <v-list class="d-flex justify-end align-center bg-blue">

              <v-list-item v-for="(item, index) in navItems" :key="index">
                <a
                  v-if="item.link"
                  :href="`${item.link}`"
                  class="cursor-pointer  text-white text-body-1 text-xl-body-large text-decoration-none"
                  :aria-label="`Go to ${item.label} ${item.link ? 'page' : ''}`"
                >
                  {{ item.label }}
                </a>
              </v-list-item>
            </v-list>
          </v-col>
        </v-row>
        <v-dialog
          v-model="dialog"
          fullscreen
          hide-overlay
          transition="dialog-bottom-transition"
        >
          <v-card color="blue">
            <v-card-item class="py-1 px-2">
              <v-toolbar color="blue">
                <a href="/" aria-label="Go to Homepage">
                  <v-img
                     :src="logo"
                :width="smAndUp ? 300 : 250"

                    height="auto"
                    aspect-ratio="16/9"
                    contain
                    alt="Country Funders Logo"
                  ></v-img>
                </a>
                <v-spacer></v-spacer>
                <v-btn icon @click.prevent="dialog = false">
                  <v-icon size="25">mdi-close</v-icon>
                </v-btn>
              </v-toolbar>
              <v-list class="px-1 bg-blue">
                <v-list-item v-for="(item, index) in navItems" :key="index">
                  <a
                    :href="`${item.link}`"
                    class="text-white text-body-1 text-decoration-none"
                    :aria-label="`Go to ${item.label} page`"
                  >
                    {{ item.label }}
                  </a>
                </v-list-item>
              </v-list>
            </v-card-item>
          </v-card>
        </v-dialog>
      </v-container>
    </v-app-bar>
  </template>
  <script>
  import { useDisplay } from "vuetify";
import logo from "/resources/assets/Logo.png"

  export default {
    components: {},
    data() {
      return {
        logo,
        dialog: false,
        currentUrl: window.location.pathname,
        navItems: [
          {
            label: "About Us",
            link: "/about-us",
          },
          {
            label: "Tourism",
            link: "/tourism",
          },
          {
            label: "News and Announcement",
            link: "/news-announcement",
          },
          {
            label: "Services",
            link: "/services",
          },
          {
            label: "Our Office",
            link: "/our-officer",
          },
        //   {
        //     label: "Contact Us",
        //     link: "/contact-us",
        //   },
        ],
      };
    },
    setup() {
      const { smAndUp } = useDisplay();
      return {
        smAndUp,
      };
    },
  };
  </script>
  <style lang="scss" scoped>
  .v-list-item--density-default:not(.v-list-item--nav).v-list-item--one-line {
    padding-inline: 10px;
  }
  </style>
