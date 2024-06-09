// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 统计每日接口调用次数 GET /api/analysis/day/top */
export async function getDailyInterfaceUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponseListUsingInterfaceCountVO>('/api/analysis/day/top', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 显示网站运营时间 GET /api/analysis/dev */
export async function getOperationTimeUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponselong>('/api/analysis/dev', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 统计每日在线人数 GET /api/analysis/login */
export async function getDailyLoginNumUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponseListUsingInterfaceCountVO>('/api/analysis/login', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 统计总共接口调用次数 GET /api/analysis/top */
export async function getTopInvokeInterfaceUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponseListAnalysisInfoBO>('/api/analysis/top', {
    method: 'GET',
    ...(options || {}),
  });
}
