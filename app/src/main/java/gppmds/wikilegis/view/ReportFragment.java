package gppmds.wikilegis.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

        import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SendEmailController;

public class ReportFragment extends Fragment implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_rerport, container, false);

        //Initializing the views
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);

        buttonSend = (Button) view.findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);
        return view;
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendEmailController sm = new SendEmailController(getContext(), email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}