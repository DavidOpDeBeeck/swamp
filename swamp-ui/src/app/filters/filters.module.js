import angular from "angular";
import DateFilter from "./date.filter";

export default angular.module( 'swamp.filters', [] )
        .filter( "toDate", DateFilter ).name;
