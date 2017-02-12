let isDate = (date) => {
    return date !== null && (new Date(date) !== "Invalid Date" && !isNaN(new Date(date))) ? true : false;
}

export default () => (text) => isDate(text) ? new Date(text).toISOString().replace("T", " ").replace("Z", "") : "-"