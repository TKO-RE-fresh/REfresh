import axios from 'axios';
import Store from '@/store/index';

axios.defaults.baseURL = 'http://localhost:80/';
axios.defaults.withCredentials = true; // 쿠키를 자동으로 포함시킴

axios.interceptors.response.use(response => response, error => {
  const status = error.response.status;
  if (status === 401) {
    // 엑세스 토큰이 만료되었을때
    return refreshTokenAndRetryRequest(error.config);
  }
  return Promise.reject(error);
});

const refreshTokenAndRetryRequest = async (config) => {
  try {
    // 토큰 갱신
    const response = await axios.get('token/cookie', {});
    const newToken = response.headers.access_token;
    Store.commit('setAccessToken', newToken);
    // 갱신 토큰 저장
    axios.defaults.headers.common.access_token = `Bearer ${newToken}`;
    return axios(config);
  } catch (e) {
    // 리프레시 토큰이 만료된 경우
    return Promise.reject(e);
  }

}

// reload 시 쿠키 존재 확인 서버 요청

export default { 
  methods: {
    async $api(url, method, { data, params }) {
      return (await axios({
        method,
        url,
        data,
        params,
      }).catch(e => console.log(e)))
    },
    async logout() {
        await axios.post('token/logout', {});
        Store.commit('setAccessToken', null);
        Store.commit('setMemberId', null);
        Store.commit('setMemberName', null);
        Store.commit('setAuth', null);
        Store.commit('setDept', null); 
    }
  }
}