import './AdminPage.css'

import {Admin, Resource} from 'react-admin'

import Authors from '../Authors/Authors'
import Posts from '../Posts/Posts'
import React from 'react'
import { createMuiTheme } from '@material-ui/core/styles';
import restProvider from 'ra-data-simple-rest'

export default function AdminPage() {
    const theme = createMuiTheme({
        palette: {
          type: 'light', // Switching the dark mode on is a single property value change.
        },
      });
     
    
     
    return (
        <div className="admin-page">
            <Admin className="admin-page" theme={theme} dataProvider={restProvider('http://localhost:3000/admin/')}>
             <Resource name='post' list={Posts}  ></Resource>
             <Resource name='author' list={Authors} ></Resource>
           </Admin>
        </div>
    )
}
