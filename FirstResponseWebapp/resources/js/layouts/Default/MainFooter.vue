<template>
    <v-footer class="bg-blue">
      <v-container class="pa-0">
        <v-row class="py-5" no-gutters>
          <v-col cols="12" lg="4" xl="3">
            <v-list density="comfortable" role="list" class="bg-transparent">
              <v-list-item
                data-aos="fade-right"
                data-aos-duration="400"
                data-aos-easing="linear"
                data-aos-offset="50"
                role="listitem"
                class="mb-2"
              >
                <a href="/" aria-label="Go to Homepage">
                  <v-img
                    :src="logo"
                    :width="smAndUp ? 400 : 250"
                    height="auto"
                    aspect-ratio="16/9"
                    contain

                  ></v-img>
                </a>
              </v-list-item>
              <v-list-item
                v-for="(detail, index) in details"
                :key="index"
                data-aos="fade-right"
                data-aos-duration="400"
                data-aos-easing="linear"
                data-aos-offset="50"
                :data-aos-delay="`${index + 1}00`"
                :aria-label="`detail-label-${index + 1}`"
                role="listitem"
                class="mb-5"
              >
                <div class="d-flex flex-row">
                  <div>
                    <v-img

                      :width="smAndUp ? 30 : 30"
                      height="30"
                      contain
                      alt="Building Logo"
                      class="me-4"
                    ></v-img>
                  </div>
                  <p
                    class="montserrat-font text-body-2 text-body-md-1 text-xl-subtitle-3 font-weight-regular"
                  >
                    {{ detail.label }}
                  </p>
                </div>
              </v-list-item>
            </v-list>
          </v-col>
          <v-col cols="12" lg="7" xl="7" offset-lg="1" offset-xl="2">
            <div class="d-flex flex-wrap justify-space-between mt-5">
              <v-list
                density="comfortable"
                role="list"
                class="bg-transparent quick-links"
              >
                <v-list-item role="listitem" class="mb-md-8">
                  <v-list-item-title>
                    <p class="text-subtitle-3 text-lg-h5 font-weight-medium">
                      Quick Links
                    </p>
                  </v-list-item-title>
                </v-list-item>
                <v-list-item
                  v-for="(item, index) in navItems"
                  :key="index"
                  role="listitem"
                  min-height="0"
                >
                  <a
                    :href="`${item.link}`"
                    class="montserrat-font text-white text-body-2 text-body-md-1 text-xl-subtitle-3 font-weight-regular text-decoration-none"
                    :aria-label="`Go to ${item.label} ${item.link ? 'page' : ''}`"
                  >
                    {{ item.label }}
                  </a>
                </v-list-item>
              </v-list>
              <v-list density="comfortable" role="list" class="bg-transparent">
                <v-list-item role="listitem" class="mb-md-7">
                  <v-list-item-title>
                    <p class="text-subtitle-3 text-lg-h5 font-weight-medium">
                      Socials
                    </p>
                  </v-list-item-title>
                </v-list-item>

                <v-list-item role="listitem">
                  <div class="d-flex flex-wrap">
                    <a
                      href="https://www.facebook.com/countryfunders"
                      target="_blank"
                      aria-label="Go to facebook page"
                    >
                      <v-img

                        :width="smAndUp ? 35 : 30"
                        :height="smAndUp ? 35 : 30"
                        contain
                        class="white-filter me-2"
                        alt="Facebook icon"
                      ></v-img>
                    </a>
                    <a
                      href="https://www.linkedin.com/company/country-funders-finance-corporation/"
                      aria-label="Go to linkedin page"
                    >
                      <v-img

                        :width="smAndUp ? 35 : 30"
                        :height="smAndUp ? 35 : 30"
                        contain
                        class="white-filter"
                        alt="Linkedin icon"
                      ></v-img>
                    </a>
                  </div>
                </v-list-item>
              </v-list>
              <v-list
                :density="smAndUp ? 'comfortable' : 'compact'"
                role="list"
                class="bg-transparent"
              >
                <v-list-item role="listitem" class="mb-md-8">
                  <v-list-item-title>
                    <p class="text-subtitle-3 text-lg-h5 font-weight-medium">
                      Say Hello
                    </p>
                  </v-list-item-title>
                </v-list-item>
                <v-list-item
                  class="montserrat-font"
                  :min-height="smAndUp ? 20 : 0"
                >
                  <a
                    href="mailto:pilarsor.pio@gmail.com"
                    target="_blank"
                    aria-label="Email country funders"
                    class="text-white text-decoration-none"
                    >pilarsor.pio@gmail.com</a
                  >
                </v-list-item>
                <v-list-item
                  class="montserrat-font"
                  :min-height="smAndUp ? 40 : 0"
                >
                   + (056) 211-3828
                </v-list-item>
              </v-list>
            </div>
          </v-col>
        </v-row>
        <v-divider class="line-color"></v-divider>
        <div class="d-flex flex-column flex-md-row justify-space-between py-5">
          <p class="text-body-2 text-md-body-1">
            Copyright © {{ new Date().getFullYear() }} Municipality of Pilar. All
            Rights Reserved.
          </p>
          <div class="text-body-2 text-md-body-1">
            <a
              href="/privacy-policy"
              target="_blank"
              class="text-decoration-none text-white"
              aria-label="Privacy Policy"
              >Privacy Policy</a
            >
            and
            <a
              href="/terms-of-use"
              target="_blank"
              class="text-decoration-none text-white"
              aria-label="Terms of Use"
              >Terms of Use</a
            >
          </div>
        </div>
      </v-container>
    </v-footer>
  </template>

  <script>
  import { useDisplay } from "vuetify";
import logo from "/resources/assets/Logo.png"
  export default {
    name: "MainFooter",
    components: {},
    data() {
      return {
        emailAddress: "",
        loading: false,
        logo,
        details: [
          {
            label:
              "",
          },
          {
            label:
              "",
          },
        ],
        navItems: [
          {
            label: "About Us",
            link: "/about-us",
          },
          {
            label: "Services",
            link: "/services",
          },
          {
            label: "New and Announcement",
            link: "/news-announcement",
          },
          {
            label: "Tourism",
            link: "/tourism",
          },
        //   {
        //     label: "Help Center",
        //     link: "/help-center",
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
  .white-filter {
    filter: brightness(0) invert(1);
  }
  .line-color {
    border-color: #fff !important;
    opacity: 19% !important;
  }
  </style>
