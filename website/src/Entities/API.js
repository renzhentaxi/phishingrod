import axios from "axios";

const base = "http://localhost:8080/api";

export class PhishingTargetAPI {
    static url = `${base}/phishingTarget`;

    static all() {
        return axios.get(PhishingTargetAPI.url);
    }

    static modify(id, phishingTarget) {
        return axios.put(`${PhishingTargetAPI.url}/${id}`, phishingTarget)
    }

    static delete(id) {
        return axios.delete(`${PhishingTargetAPI.url}/${id}`);
    }

    static add(target) {
        return axios.post(`${PhishingTargetAPI.url}`, target);
    }
}

export class SpoofTargetAPI {
    static url = `${base}/spoofTarget`;

    static all() {
        return axios.get(SpoofTargetAPI.url);
    }

    static modify(id, spoofTarget) {
        return axios.put(`${SpoofTargetAPI.url}/${id}`, spoofTarget)
    }

    static delete(id) {
        return axios.delete(`${SpoofTargetAPI.url}/${id}`);
    }

    static add(target) {
        return axios.post(`${SpoofTargetAPI.url}`, target);
    }
}