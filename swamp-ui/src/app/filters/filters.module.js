import Angular from "angular";
import DateFilter from "./date.filter";
import trustedHTMLFilter from "./trusted-html.filter";

const module = Angular.module('swamp.filters', [])
    .filter("toDate", DateFilter)
    .filter('toTrustedHTML', trustedHTMLFilter);

export default module.name;