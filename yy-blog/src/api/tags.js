import request from "../utils/request";

export function getTagsList() {
  return request ({
    url: '/tags/getTagsList',
    method: 'get',
  })
}
