export default {
  props: {
    promise: {
      default: null,
      type: Promise,
    },
  },
  data() {
    return {
      data: null,
      error: null,
      pending: false,
      resolved: null,
    };
  },
  watch: {
    promise: {
      immediate: true,
      async handler() {
        if (!this.promise) {return;}

        try {
          this.status({ pending: true });
          const { data } = await this.promise;
          this.status({ data, resolved: true });
        } catch (error) {
          this.status({
            data: null,
            error,
            resolved: false,
          });
        }
      },
    },
  },
  methods: {
    status({
      data = this.data,
      error = null,
      pending = false,
      resolved = null,
    }) {
      this.data = data;
      this.error = error;
      this.pending = pending;
      this.resolved = resolved;
    },
  },
  render() {
    return this.$scopedSlots.default({
      data: this.data,
      status: {
        error: this.error,
        pending: this.pending,
        resolved: this.resolved,
      },
    });
  },
};