/* eslint-disable react/prop-types */
import { DialogContent } from "@mui/material";


export default function TurnDialog({turn}) {
    // TODO hahmon vanhat kortit
    return(
        <DialogContent>
            <div>
                <button onClick={() => console.log(turn)} />
            </div>
        </DialogContent>
    )
}