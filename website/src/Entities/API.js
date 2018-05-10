import axios from "axios";

const base = "http://localhost:8080/api/";

function stripData(entity) {
    const stripped = Object.assign({}, entity);
    delete stripped.id;
    delete stripped.createdOn;
    delete stripped.lastModifiedOn;
    return stripped;
}

class API {
    constructor(url) {
        this.url = base + url;
    }

    instanceUrl(id) {
        return `${this.url}/${id}`
    }

    all() {
        return axios.get(this.url);
    }

    modify(id, entity) {
        return axios.put(this.instanceUrl(id), stripData(entity));
    }

    delete(id) {
        return axios.delete(this.instanceUrl(id));
    }

    add(entity) {
        return axios.post(this.url, entity);
    }

    post(url, data) {
        return axios.post(this.url + url, data);
    }
}

export const PhishingTargetAPI = new API("phishingTarget");
export const SpoofTargetAPI = new API("spoofTarget");
export const SenderAPI = new API("sender");
export const SenderServerAPI = new API("senderServer");
export const EmailTemplateAPI = new API("emailTemplate");
export const AttemptAPI = new API("attempt");