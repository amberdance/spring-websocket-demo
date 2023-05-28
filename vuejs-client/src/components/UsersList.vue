<template>
  <div style="width: 100%">
    <el-button-group
      style="margin: 1rem 0; display: flex; justify-content: center"
    >
      <el-button
        v-if="isConnectionClosed"
        size="small"
        type="success"
        @click="connect"
        >Connect to websocket
      </el-button>
      <el-button v-else size="small" type="danger" @click="disconnect"
        >Disconnect
      </el-button>
      <el-button size="small" type="primary" @click="fetchUsers"
        >Fetch users
      </el-button>
    </el-button-group>

    <el-table
      v-loading="isLoading"
      :data="users"
      :default-sort="{ prop: 'id', order: 'descending' }"
      :row-class-name="tableRowClassName"
      border
      class="table"
      height="90vh"
    >
      <el-table-column align="center" label="Id" prop="id" width="100" />
      <el-table-column label="Email" prop="email" />
      <el-table-column label="First Name" prop="firstName" />
      <el-table-column label="Last name" prop="lastName" />
      <el-table-column label="Role" prop="role" />
      <el-table-column
        :formatter="booleanFormatter"
        label="Enabled"
        prop="enabled"
      />
    </el-table>
  </div>
</template>

<script>
import { Message } from "element-ui";

export default {
  data() {
    return {
      websocket: null,
      isLoading: false,
      users: [],
    };
  },

  computed: {
    isConnectionClosed() {
      return this.websocket == null;
    },
  },

  methods: {
    createWebSocket() {
      this.websocket = new WebSocket(
        "ws://" +
          process.env.VUE_APP_API_URL +
          process.env.VUE_APP_WEBSOCKET_USERS_ENDPOINT
      );

      this.websocket.onopen = () =>
        Message.success({
          message: "Connection established",
          offset: 20,
          duration: 1500,
          showClose: true,
        });

      this.websocket.onclose = () =>
        Message.info({
          message: "Connection closed",
          offset: 20,
          duration: 2000,
          showClose: true,
        });

      this.websocket.onmessage = (message) => this.processUser(message);
    },

    connect() {
      this.createWebSocket();
    },

    disconnect() {
      this.websocket.close();
      this.websocket = null;
    },

    tableRowClassName({ row }) {
      return row.enabled ? "row-success" : "row-error";
    },

    processUser(message) {
      this.users.unshift(JSON.parse(message.data));
    },

    booleanFormatter({ enabled }) {
      return enabled + "";
    },

    async fetchUsers() {
      try {
        this.isLoading = true;
        const response = await this.$axios.get(
          "http://" + process.env.VUE_APP_API_URL + "/users"
        );

        this.users = response.data;
      } catch (e) {
        Message.error({
          message: e,
          offset: 20,
          duration: 2000,
          showClose: true,
        });
        console.error(e);
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>
