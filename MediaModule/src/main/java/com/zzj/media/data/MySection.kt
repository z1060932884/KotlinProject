package com.zzj.media.data

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class MySection : SectionEntity<Video> {
    var isMore: Boolean = false

    constructor(isHeader: Boolean, header: String, isMroe: Boolean) : super(isHeader, header) {
        this.isMore = isMroe
    }

    constructor(t: Video) : super(t) {}
}
