import { defineStore } from 'pinia'

export const Theme = defineStore('themestate', {
  state: () => ({
    mode: 'dark'
  }),

  actions: {
    async toggle(value) {
      localStorage.setItem('mode', value)
    }
  }
})
