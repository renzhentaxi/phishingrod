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
}