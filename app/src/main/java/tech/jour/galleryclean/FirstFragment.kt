package tech.jour.galleryclean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.orhanobut.logger.Logger
import tech.jour.galleryclean.databinding.FragmentFirstBinding
import tech.jour.galleryclean.databinding.ItemGridImageBinding
import tech.jour.galleryclean.entry.Photo
import tech.jour.galleryclean.util.PermissionsUtils

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener { view ->

            findNavController().navigate(R.id.SecondFragment)

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.fab)
//                .setAction("Action", null).show()
        }

        PermissionsUtils.getPermissions(requireActivity())

        mainViewModel.photos.observe(viewLifecycleOwner) {
            Logger.d(it.size)
            binding.photosRv.adapter = PhotoAdapter(it)
        }
    }

    inner class PhotoAdapter(private val data: List<Photo>) :
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemGridImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemGridImageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Photo) {
                binding.image.load(bean.path)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}