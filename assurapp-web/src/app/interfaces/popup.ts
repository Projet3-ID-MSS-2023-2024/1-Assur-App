import {PopupType} from "../enums/popup-type";

export interface Popup {
  id: number,
  message: string,
  type: PopupType
}
