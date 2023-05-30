import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8090/';
export default {
  methods: {
    async $api(url, method, data) {
      return (await axios({
        method,
        url,
        data
      }).catch(e => console.log(e))).data
    }
  }
}