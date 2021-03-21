package com.test.gittracker;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.imageview.ShapeableImageView;

import java.lang.ref.WeakReference;

import static com.test.gittracker.Utils.getActivity;

class RepositoryComponent {
    private final WeakReference<Repository> repositoryRef;
    private final View convertView;
    private final TextView textViewAppName;
    private final TextView textViewOwner;
    private final TextView textViewDesc;
    private final TextView textViewLanguage;
    private final CardView card;

    public RepositoryComponent(View convertView, WeakReference<Repository> repositoryRef) {
        this.repositoryRef = repositoryRef;
        this.convertView = convertView;
        Repository repository = repositoryRef.get();

        textViewAppName = convertView.findViewById(R.id.text_view_app_name);
        textViewOwner = convertView.findViewById(R.id.text_view_owner);
        textViewDesc = convertView.findViewById(R.id.text_view_description);
        textViewLanguage = convertView.findViewById(R.id.text_view_language);
        card = convertView.findViewById(R.id.card);

        textViewAppName.setText(repository.getName());
        textViewOwner.setText(repository.getOwner().getLogin());

        String language = repository.getLanguage();
        if (language.equals("null")) textViewLanguage.setVisibility(View.GONE);
        else textViewLanguage.setText(language);

        String description = repository.getDescription();
        if (description.equals("null")) textViewDesc.setVisibility(View.GONE);
        else textViewDesc.setText(description);

        if (repository.isPrivate()) {
            ShapeableImageView imgPublic = convertView.findViewById(R.id.imgPublic);
            imgPublic.setImageResource(R.drawable.ic_padlock);
        }

        card.setOnClickListener(showRepoData);
    }

    private final View.OnClickListener showRepoData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Repository repository = repositoryRef.get();



//            if (repository.isDetailed()) new UserDialog().showDialog(getActivity(convertView), user);

//            else {


//                RepositoryDetailsAsyncTask taskRepo = new RepositoryDetailsAsyncTask(new WeakReference<>(repository), convertView, login, token);
//                taskRepo.execute("https://api.github.com/users/" +  repository.getLogin() + "?accept=application/vnd.github.v3+json");
                new RepositoryDialog().showDialog(getActivity(convertView), repository);
//            }
        }
    };
}
