/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";
import 'vuetify/dist/vuetify.min.css'
// Composables
import { createVuetify } from "vuetify";
import { VBtn } from "vuetify/components";

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    themes: {
      light: {
        colors: {
            primary: '#5F91C8',
            secondary: '#1D508F',
            "deep-blue": '#10304B',
            "dark-blue": '#1E293B',
            "blue1": "#006EAD",
            red: "#FF0000",
            gray: "#EFF1F1",
            "gray-light-cs": "#E6E6E605"
        },
      },
    },
  },
  aliases: {
    VBtnPrimary: VBtn,
    VBtnSecondary: VBtn,
    VBtnTertiary: VBtn,
  },
  defaults: {
    VBtn: {
      class: "text-none",
    },
    VBtnPrimary: {
      rounded: "lg",
      color: "primary",
      variant: "flat",
      class: "text-none font-weight-medium",
      height: "50",
      minWidth: 160,
    },
    VBtnSecondary: {
      rounded: "lg",
      color: "secondary",
      variant: "flat",
      class: "text-none text-black font-weight-medium",
      height: "50",
    },
    VBtnTertiary: {
      rounded: "lg",
      color: "light-primary",
      variant: "flat",
      class: "text-none font-weight-medium",
      height: "50",
    },
  },
});
