// Generated code from Butter Knife. Do not modify!
package com.netease.nim.demo.task.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.netease.nim.demo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddActivityActivity_ViewBinding implements Unbinder {
  private AddActivityActivity target;

  @UiThread
  public AddActivityActivity_ViewBinding(AddActivityActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddActivityActivity_ViewBinding(AddActivityActivity target, View source) {
    this.target = target;

    target.listView = Utils.findRequiredViewAsType(source, R.id.listView, "field 'listView'", ListView.class);
    target.tv_task_name = Utils.findRequiredViewAsType(source, R.id.tv_task_name, "field 'tv_task_name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddActivityActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listView = null;
    target.tv_task_name = null;
  }
}
