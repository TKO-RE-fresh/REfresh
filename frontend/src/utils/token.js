import Cookies from 'js-cookie';
export default {
  removeToken: () => {
    Cookies.remove('Refresh_Token');
  },
}