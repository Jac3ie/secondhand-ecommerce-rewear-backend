<template>
  <v-container>
    <h2>Home</h2>
    <ul>
      <li>
        <router-link to="/" class="btn btn-primary">Go to Home</router-link>
      </li>

      <li>
        <router-link to="/about" class="btn btn-primary">Go to About</router-link>
      </li>

      <li>
        <router-link to="/login" class="btn btn-primary">Go to Login</router-link>
      </li>
    </ul>
  </v-container>
</template>

<script setup lang="ts">
// No additional logic needed for now.
</script>

<style scoped lang="sass">
/* Add your styles here if needed */
</style>
