import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlmacenAdapter extends BaseAdapter {

    Context context;
    List<ModeloAlmacen> list;

    public AlmacenAdapter(Context context, List<ModeloAlmacen> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageAlmacen;
        TextView nombreAlmacen;

        ModeloAlmacen c = list.get(i);

        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_almacen, null);
        }
        imageAlmacen = view.findViewById(R.id.img_Almacen);
        nombreAlmacen = view.findViewById(R.id.txtAlmacen);

        nombreAlmacen.setText(c.getNombre());
        imageAlmacen.setImageResource(c.getImagen());
        return view;
    }
}