import Angular from "angular";
import DateFilter from "./date.filter";

const module = Angular.module('swamp.filters', [])
    .filter("toDate", DateFilter);

export default module.name;