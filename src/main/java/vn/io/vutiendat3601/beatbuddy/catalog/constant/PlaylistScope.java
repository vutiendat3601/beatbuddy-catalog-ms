package vn.io.vutiendat3601.beatbuddy.catalog.constant;

import org.keycloak.representations.idm.authorization.ScopeRepresentation;

public enum PlaylistScope {
    PLAYLIST_VIEW_PUBLIC("playlist:view-public"),
    PLAYLIST_VIEW("playlist:view"),
    PLAYLIST_EDIT("playlist:edit"),
    PLAYLIST_DELETE("playlist:delete");

    private ScopeRepresentation scope;

    private PlaylistScope(String name) {
        scope = new ScopeRepresentation(name);
    }

    public ScopeRepresentation instance() {
        return scope;
    }
}
