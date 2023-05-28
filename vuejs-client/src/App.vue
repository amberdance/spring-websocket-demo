<template>
  <div id="app">
    <el-container class="container">
      <UsersList />
    </el-container>
  </div>
</template>

<script>
import UsersList from "@/components/UsersList";

export default {
  name: "app",
  components: {
    UsersList,
  },
};
</script>
