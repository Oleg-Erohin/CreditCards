import { IBank } from "./IBank";
import { IFilterData } from "./IFilterData";

export interface IFilterPropsType {
    setFiltersData: (filterData: IFilterData) => void;
    banks: IBank[];
}
