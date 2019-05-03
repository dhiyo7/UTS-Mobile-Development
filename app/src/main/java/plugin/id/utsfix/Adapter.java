package plugin.id.utsfix;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haerul on 17/03/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Command> contacts;
    private Context context;

    public Adapter(List<Command> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.email.setText(contacts.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,email;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra("id", contacts.get(getAdapterPosition()).getId());
            i.putExtra("name", contacts.get(getAdapterPosition()).getName());
            i.putExtra("email", contacts.get(getAdapterPosition()).getEmail());
            context.startActivity(i);

        }
    }
}
