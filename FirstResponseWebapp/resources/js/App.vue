<template>
    <v-app>
        <v-layout full-height style="overflow: unset;">

      <router-view name="left-nav"></router-view>

      <v-main style="height: 250px">
        <router-view name="top-nav"></router-view>
        <router-view ></router-view>

      </v-main>
    </v-layout>
    </v-app>
</template>

<script>
import { Profile } from './stores/profilestate';

export default {
name: 'App',
  created() {
    const profileStore = Profile();
    profileStore.loadStorageData();
  }
};
</script>

<style lang="scss">
:root {
  // put child modal on top - ckeditor
  --ck-z-default: 10000 !important;
  --ck-z-modal: calc( var(--ck-z-default) + 999 );

  /* Overrides the border radius setting in the theme. */
  --ck-border-radius: 4px;

  /* Overrides the default font size in the theme. */
  --ck-font-size-base: 14px;

  /* Helper variables to avoid duplication in the colors. */
  --ck-color-base-background: transparent !important;
  --ck-custom-background: hsl(270, 1%, 29%) !important;
  --ck-custom-foreground: hsl(255, 3%, 18%) !important;
  --ck-custom-border: hsl(300, 1%, 22%) !important;
  --ck-custom-white: hsl(0, 0%, 100%) !important;

  /* -- Overrides generic colors. ------------------------------------------------------------- */

  --ck-color-base-foreground: var(--ck-custom-background) !important;
  --ck-color-focus-border: hsl(208, 90%, 62%) !important;
  --ck-color-text: hsl(0, 0%, 98%) !important;
  --ck-color-shadow-drop: hsla(0, 0%, 0%, 0.2) !important;
  --ck-color-shadow-inner: hsla(0, 0%, 0%, 0.1) !important;

  /* -- Overrides the default .ck-button class colors. ---------------------------------------- */

  --ck-color-button-default-background: var(--ck-custom-background) !important;
  --ck-color-button-default-hover-background: hsl(270, 1%, 22%) !important;
  --ck-color-button-default-active-background: hsl(270, 2%, 20%) !important;
  --ck-color-button-default-active-shadow: hsl(270, 2%, 23%) !important;
  --ck-color-button-default-disabled-background: var(--ck-custom-background) !important;

  --ck-color-button-on-background: var(--ck-custom-foreground) !important;
  --ck-color-button-on-hover-background: hsl(255, 4%, 16%) !important;
  --ck-color-button-on-active-background: hsl(255, 4%, 14%) !important;
  --ck-color-button-on-active-shadow: hsl(240, 3%, 19%) !important;
  --ck-color-button-on-disabled-background: var(--ck-custom-foreground) !important;

  --ck-color-button-action-background: hsl(168, 76%, 42%) !important;
  --ck-color-button-action-hover-background: hsl(168, 76%, 38%) !important;
  --ck-color-button-action-active-background: hsl(168, 76%, 36%) !important;
  --ck-color-button-action-active-shadow: hsl(168, 75%, 34%) !important;
  --ck-color-button-action-disabled-background: hsl(168, 76%, 42%) !important;
  --ck-color-button-action-text: var(--ck-custom-white) !important;

  --ck-color-button-save: hsl(120, 100%, 46%) !important;
  --ck-color-button-cancel: hsl(15, 100%, 56%) !important;

  /* -- Overrides the default .ck-dropdown class colors. -------------------------------------- */

  --ck-color-dropdown-panel-background: var(--ck-custom-background) !important;
  --ck-color-dropdown-panel-border: var(--ck-custom-foreground) !important;

  /* -- Overrides the default .ck-splitbutton class colors. ----------------------------------- */

  --ck-color-split-button-hover-background: var(--ck-color-button-default-hover-background) !important;
  --ck-color-split-button-hover-border: var(--ck-custom-foreground) !important;

  /* -- Overrides the default .ck-input class colors. ----------------------------------------- */

  --ck-color-input-background: var(--ck-custom-background) !important;
  --ck-color-input-border: hsl(257, 3%, 43%) !important;
  --ck-color-input-text: hsl(0, 0%, 98%) !important;
  --ck-color-input-disabled-background: hsl(255, 4%, 21%) !important;
  --ck-color-input-disabled-border: hsl(250, 3%, 38%) !important;
  --ck-color-input-disabled-text: hsl(0, 0%, 78%) !important;

  /* -- Overrides the default .ck-labeled-field-view class colors. ---------------------------- */

  --ck-color-labeled-field-label-background: var(--ck-custom-background) !important;

  /* -- Overrides the default .ck-list class colors. ------------------------------------------ */

  --ck-color-list-background: var(--ck-custom-background) !important;
  --ck-color-list-button-hover-background: var(--ck-custom-foreground) !important;
  --ck-color-list-button-on-background: hsl(208, 88%, 52%) !important;
  --ck-color-list-button-on-text: var(--ck-custom-white) !important;

  /* -- Overrides the default .ck-balloon-panel class colors. --------------------------------- */
  --ck-color-panel-background: var(--ck-custom-background) !important;
  --ck-color-panel-border: var(--ck-custom-border) !important;

  /* -- Overrides the default .ck-toolbar class colors. --------------------------------------- */

  --ck-color-toolbar-background: var(--ck-custom-background) !important;
  --ck-color-toolbar-border: var(--ck-custom-border) !important;

  /* -- Overrides the default .ck-tooltip class colors. --------------------------------------- */

  --ck-color-tooltip-background: hsl(252, 7%, 14%) !important;
  --ck-color-tooltip-text: hsl(0, 0%, 93%) !important;

  /* -- Overrides the default colors used by the ckeditor5-image package. --------------------- */

  --ck-color-image-caption-background: hsl(0, 0%, 97%) !important;
  --ck-color-image-caption-text: hsl(0, 0%, 20%) !important;

  /* -- Overrides the default colors used by the ckeditor5-widget package. -------------------- */

  --ck-color-widget-blurred-border: hsl(0, 0%, 87%) !important;
  --ck-color-widget-hover-border: hsl(43, 100%, 68%);
  --ck-color-widget-editable-focus-background: var(--ck-custom-white) !important;

  /* -- Overrides the default colors used by the ckeditor5-link package. ---------------------- */

  --ck-color-link-default: hsl(190, 100%, 75%) !important;
}

.v-theme--dark {
  // /* Overrides the border radius setting in the theme. */
  // --ck-border-radius: 4px;

  // /* Overrides the default font size in the theme. */
  // --ck-font-size-base: 14px;

  // /* Helper variables to avoid duplication in the colors. */
  // --ck-custom-background: hsl(270, 1%, 29%) !important;
  // --ck-custom-foreground: hsl(255, 3%, 18%) !important;
  // --ck-custom-border: hsl(300, 1%, 22%) !important;
  // --ck-custom-white: hsl(0, 0%, 100%) !important;

  // /* -- Overrides generic colors. ------------------------------------------------------------- */

  // --ck-color-base-foreground: var(--ck-custom-background) !important;
  // --ck-color-focus-border: hsl(208, 90%, 62%) !important;
  // --ck-color-text: hsl(0, 0%, 98%) !important;
  // --ck-color-shadow-drop: hsla(0, 0%, 0%, 0.2) !important;
  // --ck-color-shadow-inner: hsla(0, 0%, 0%, 0.1) !important;

  // /* -- Overrides the default .ck-button class colors. ---------------------------------------- */

  // --ck-color-button-default-background: var(--ck-custom-background) !important;
  // --ck-color-button-default-hover-background: hsl(270, 1%, 22%) !important;
  // --ck-color-button-default-active-background: hsl(270, 2%, 20%) !important;
  // --ck-color-button-default-active-shadow: hsl(270, 2%, 23%) !important;
  // --ck-color-button-default-disabled-background: var(--ck-custom-background) !important;

  // --ck-color-button-on-background: var(--ck-custom-foreground) !important;
  // --ck-color-button-on-hover-background: hsl(255, 4%, 16%) !important;
  // --ck-color-button-on-active-background: hsl(255, 4%, 14%) !important;
  // --ck-color-button-on-active-shadow: hsl(240, 3%, 19%) !important;
  // --ck-color-button-on-disabled-background: var(--ck-custom-foreground) !important;

  // --ck-color-button-action-background: hsl(168, 76%, 42%) !important;
  // --ck-color-button-action-hover-background: hsl(168, 76%, 38%) !important;
  // --ck-color-button-action-active-background: hsl(168, 76%, 36%) !important;
  // --ck-color-button-action-active-shadow: hsl(168, 75%, 34%) !important;
  // --ck-color-button-action-disabled-background: hsl(168, 76%, 42%) !important;
  // --ck-color-button-action-text: var(--ck-custom-white) !important;

  // --ck-color-button-save: hsl(120, 100%, 46%) !important;
  // --ck-color-button-cancel: hsl(15, 100%, 56%) !important;

  // /* -- Overrides the default .ck-dropdown class colors. -------------------------------------- */

  // --ck-color-dropdown-panel-background: var(--ck-custom-background) !important;
  // --ck-color-dropdown-panel-border: var(--ck-custom-foreground) !important;

  // /* -- Overrides the default .ck-splitbutton class colors. ----------------------------------- */

  // --ck-color-split-button-hover-background: var(--ck-color-button-default-hover-background) !important;
  // --ck-color-split-button-hover-border: var(--ck-custom-foreground) !important;

  // /* -- Overrides the default .ck-input class colors. ----------------------------------------- */

  // --ck-color-input-background: var(--ck-custom-background) !important;
  // --ck-color-input-border: hsl(257, 3%, 43%) !important;
  // --ck-color-input-text: hsl(0, 0%, 98%) !important;
  // --ck-color-input-disabled-background: hsl(255, 4%, 21%) !important;
  // --ck-color-input-disabled-border: hsl(250, 3%, 38%) !important;
  // --ck-color-input-disabled-text: hsl(0, 0%, 78%) !important;

  // /* -- Overrides the default .ck-labeled-field-view class colors. ---------------------------- */

  // --ck-color-labeled-field-label-background: var(--ck-custom-background) !important;

  // /* -- Overrides the default .ck-list class colors. ------------------------------------------ */

  // --ck-color-list-background: var(--ck-custom-background) !important;
  // --ck-color-list-button-hover-background: var(--ck-custom-foreground) !important;
  // --ck-color-list-button-on-background: hsl(208, 88%, 52%) !important;
  // --ck-color-list-button-on-text: var(--ck-custom-white) !important;

  // /* -- Overrides the default .ck-balloon-panel class colors. --------------------------------- */
  // --ck-color-panel-background: var(--ck-custom-background) !important;
  // --ck-color-panel-border: var(--ck-custom-border) !important;

  // /* -- Overrides the default .ck-toolbar class colors. --------------------------------------- */

  // --ck-color-toolbar-background: var(--ck-custom-background) !important;
  // --ck-color-toolbar-border: var(--ck-custom-border) !important;

  // /* -- Overrides the default .ck-tooltip class colors. --------------------------------------- */

  // --ck-color-tooltip-background: hsl(252, 7%, 14%) !important;
  // --ck-color-tooltip-text: hsl(0, 0%, 93%) !important;

  // /* -- Overrides the default colors used by the ckeditor5-image package. --------------------- */

  // --ck-color-image-caption-background: hsl(0, 0%, 97%) !important;
  // --ck-color-image-caption-text: hsl(0, 0%, 20%) !important;

  // /* -- Overrides the default colors used by the ckeditor5-widget package. -------------------- */

  // --ck-color-widget-blurred-border: hsl(0, 0%, 87%) !important;
  // --ck-color-widget-hover-border: hsl(43, 100%, 68%);
  // --ck-color-widget-editable-focus-background: var(--ck-custom-white) !important;

  // /* -- Overrides the default colors used by the ckeditor5-link package. ---------------------- */

  // --ck-color-link-default: hsl(190, 100%, 75%) !important;
}

.v-theme--dark .v-navigation-drawer__content, .card-content {
  overflow-y: overlay!important;
  &::-webkit-scrollbar {
    width: 5px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: #3e3e3e;
    border-radius: 7px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: white;
  }
}

.v-theme--light .v-navigation-drawer__content, .card-content {
  overflow-y: overlay!important;
  &::-webkit-scrollbar {
    width: 5px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    background: #b0b0b0;
    border-radius: 7px;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: #3e3e3e;
  }
}



// vue-transition
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.32s ease-in-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
