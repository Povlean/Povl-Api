// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** getLogDataList GET /api/sys */
export async function getLogDataListUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponseListLogDataVO>('/api/sys', {
    method: 'GET',
    ...(options || {}),
  });
}
